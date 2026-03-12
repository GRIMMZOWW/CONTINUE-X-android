# CONTINUE-X Android ⚡

> The Android app for CONTINUE-X — compress your AI chat into a smart Capsule and resume instantly.

**📲 [Download APK v1.0.0 — Free](https://github.com/GRIMMZOWW/CONTINUE-X-android/releases/download/v1.0.0/app-release.apk)**

Android 8.0+ &nbsp;|&nbsp; ~14.2 MB &nbsp;|&nbsp; No signup required

---

## 😤 The Problem

When your AI chat gets too long it slows down, loses context, and you're forced to start over and re-explain everything from scratch.

## ✅ The Fix

Paste your long AI conversation → get a compressed smart **Capsule** → paste into a new chat → AI picks up exactly where you left off.

---

## ✨ Features

- 📦 **3 Capsule Styles** — Brief, Detailed, Code-focused
- ✨ **Smart Resume Prompt** — Auto-generates the perfect prompt to continue
- 💾 **Download as .txt** — Save Capsule to your Downloads folder
- 🔒 **Zero Data Storage** — Your chats are never saved or logged
- ⚡ **No Signup Required** — Open and use instantly
- 🌐 **Works with any AI** — Claude, ChatGPT, Cursor, Gemini and more
- 🔑 **Custom API Key** — Bring your own Groq or OpenAI key

---

## 🛠️ Tech Stack

- **Language** — Kotlin
- **UI** — Jetpack Compose
- **Architecture** — MVVM
- **Networking** — Retrofit + OkHttp
- **State** — StateFlow + ViewModel
- **API** — Groq via CONTINUE-X backend

---

## 🚀 Build From Source

```bash
git clone https://github.com/GRIMMZOWW/CONTINUE-X-android.git
```

Open in Android Studio → Run on emulator or device.

The app calls `https://continue-x.vercel.app/api/generate` — no extra setup needed.

---

## 📦 Install APK

1. Download the APK from the [Releases page](https://github.com/GRIMMZOWW/CONTINUE-X-android/releases)
2. On your phone go to **Settings → Security → Install unknown apps**
3. Allow your browser or file manager to install APKs
4. Open the downloaded APK and tap **Install**

---

## 🔒 Privacy

Chat text is sent to the API only to generate the Capsule. Nothing is stored in any database. HTTP logging is fully disabled in production. API keys are stored locally in SharedPreferences only.

---

## 🌐 Web Version

👉 [continue-x.vercel.app](https://continue-x.vercel.app)

---

## 👨‍💻 Built By

**Bhaumik** — Vibecoder & AI Builder

- GitHub: [@GRIMMZOWW](https://github.com/GRIMMZOWW)
- Web: [continue-x.vercel.app](https://continue-x.vercel.app)

---

## 📄 License

MIT — free to use, fork, and build on.

---

<p align="center">Built with ⚡ by Bhaumik &nbsp;—&nbsp; If this helped you, drop a ⭐</p>
