import sys
import whisper
import requests
import os
import warnings
import sys
sys.stdout.reconfigure(encoding='utf-8')
warnings.filterwarnings("ignore")

def summarize_text(text):
    API_URL = "https://api-inference.huggingface.co/models/facebook/bart-large-cnn"
    headers = {"Authorization": f"Bearer {os.getenv('HF_TOKEN', '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')}"}

    response = requests.post(API_URL, headers=headers, json={"inputs": text})
    
    data = response.json()
    
    # 리스트인지 딕셔너리인지 확인
    if isinstance(data, list) and len(data) > 0 and "summary_text" in data[0]:
        summary = data[0]["summary_text"]
    elif isinstance(data, dict) and "summary_text" in data:
        summary = data["summary_text"]
    else:
        # API 반환값에 summary_text가 없으면 원본 텍스트 반환
        summary = text
    
    return summary

def transcribe_and_summarize(audio_path):
    model = whisper.load_model("base")
    result = model.transcribe(audio_path)
    text = result["text"]
    summary = summarize_text(text)
    return summary

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python summarize_audio.py <audio_path>")
        sys.exit(1)

    audio_path = sys.argv[1]
    summary = transcribe_and_summarize(audio_path)
    print(summary)
