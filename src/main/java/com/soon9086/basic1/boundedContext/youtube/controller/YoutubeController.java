package com.soon9086.basic1.boundedContext.youtube.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/youtube")
public class YoutubeController {

    private volatile double progress = 0.0;

    // ✅ 진행률 SSE (프론트에서 EventSource로 수신)
    @GetMapping(value = "/progress", produces = "text/event-stream")
    public SseEmitter progressEmitter() {
        SseEmitter emitter = new SseEmitter(0L);
        new Thread(() -> {
            try {
                while (progress < 100.0) {
                    emitter.send(SseEmitter.event().data(progress));
                    Thread.sleep(800);
                }
                emitter.send(SseEmitter.event().data("done"));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }

    @PostMapping("/extractAndSummarize")
    public ResponseEntity<Map<String, Object>> extractAndSummarize(@RequestBody Map<String, String> body) {
        String videoUrl = body.get("url");
        if (videoUrl == null || videoUrl.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "영상 URL이 없습니다."
            ));
        }

        String outputDir = "downloads";
        File dir = new File(outputDir);
        // ✅ 기존 파일 삭제
        if (dir.exists()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) file.delete();
        } else {
            dir.mkdirs();
        }

        String filename = "audio_" + System.currentTimeMillis() + ".mp3";
        File outputFile = new File(dir, filename);

        List<String> command = List.of(
                "yt-dlp",
                "-x", "--audio-format", "mp3",
                "-o", outputFile.getAbsolutePath(),
                videoUrl
        );

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);

                    // ✅ 진행률 추출
                    if (line.contains("[download]")) {
                        int percentIndex = line.indexOf('%');
                        if (percentIndex > 0) {
                            try {
                                String percentStr = line.substring(11, percentIndex).trim();
                                progress = Double.parseDouble(percentStr);
                            } catch (Exception ignored) {}
                        }
                    }
                }
            }

            // ✅ yt-dlp 종료 기다림
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message", "오디오 추출 중 오류 발생!"
                ));
            }

            // ✅ 오디오 추출 후 요약
            String audioPath = outputFile.getAbsolutePath();
            String summary = summarizeAudio(audioPath).trim();

            String downloadUrl = "/downloads/" + filename;
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("filename", filename);
            response.put("downloadUrl", downloadUrl);
            response.put("summary", summary);
            response.put("message", "변환 및 요약 성공!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "서버 오류: " + e.getMessage()
            ));
        }
    }

    // ✅ 오디오 파일 요약 (Python 스크립트 연동)
    private String summarizeAudio(String audioPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("python", "summarize_audio.py", audioPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        process.waitFor();
        return output.toString();
    }

}


