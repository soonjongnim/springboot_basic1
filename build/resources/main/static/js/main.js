document.addEventListener('DOMContentLoaded', () => {
    console.log('메인 페이지 로드 완료');

    const convertBtn = document.getElementById("convertBtn");
    const videoUrlInput = document.getElementById("videoUrl");
    const statusArea = document.getElementById("statusArea");
    const statusText = document.getElementById("statusText");
    const progressBar = document.getElementById("progressBar");
    const resultArea = document.getElementById("resultArea");

    convertBtn.addEventListener("click", async () => {
        const videoUrl = videoUrlInput.value.trim();
        if (!videoUrl) {
            alert("🎥 유튜브 영상 URL을 입력해주세요!");
            return;
        }

        // 초기화
        statusArea.style.display = "block";
        statusText.textContent = "오디오 추출 준비 중...";
        progressBar.style.width = "0%";
        resultArea.style.display = "none";
        resultArea.innerHTML = "";

        // ✅ SSE (진행률 표시)
        const evtSource = new EventSource("/youtube/progress");

        evtSource.onmessage = (event) => {
            const data = event.data.trim();

            if (data === "done") {
                progressBar.style.width = "100%";
                statusText.textContent = "다운로드 완료! 요약 중...";
                evtSource.close();
                return;
            }

            const percent = parseFloat(data);
            if (!isNaN(percent)) {
                progressBar.style.width = percent + "%";
                statusText.textContent = `다운로드 중... ${percent.toFixed(1)}%`;
            }
        };

        evtSource.onerror = () => {
            console.warn("SSE 연결 종료");
            evtSource.close();
        };

        // ✅ 백엔드로 요청 보내기
        try {
            const response = await fetch("/youtube/extractAndSummarize", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ url: videoUrl })
            });

            const data = await response.json();

            if (!data.success) {
                statusText.textContent = "❌ 오류: " + data.message;
                return;
            }

            // ✅ 요약 결과 및 다운로드 버튼 표시
            statusText.textContent = "✅ 요약 완료!";
            resultArea.style.display = "block";
            resultArea.innerHTML = `
                <div class="summary-block">
                    <h3>📝 요약 결과</h3>
                    <p>${data.summary}</p>
                </div>
                <div class="download-block" style="margin-top:15px;">
                    <a href="${data.downloadUrl}" class="btn-download" download>
                        📥 오디오 다운로드
                    </a>
                </div>
            `;
        } catch (err) {
            console.error(err);
            statusText.textContent = "⚠️ 서버 오류: " + err.message;
        }
    });
});
