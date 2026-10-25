# Omnidroid · 3DS Edition

<p align="center">
  <img src="docs/assets/cover.png?v=3" alt="Omnidroid 3DS Edition cover" width="100%" />
</p>

<p align="center">
  <a href="https://github.com/bvnkfatboy/Omnidroid/releases"><img src="https://img.shields.io/github/v/release/bvnkfatboy/Omnidroid?style=for-the-badge&logo=android&color=3DDC84" alt="Release"></a>
  <a href="https://github.com/bvnkfatboy/Omnidroid/stargazers"><img src="https://img.shields.io/github/stars/bvnkfatboy/Omnidroid?style=for-the-badge&logo=github&color=FFD700" alt="Stars"></a>
  <a href="./COPYING"><img src="https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge" alt="License: GPLv3"></a>
</p>

**Omnidroid 3DS Edition** is a Libretro-powered multi-console emulator for Android with a full **Nintendo 3DS HOME Menu–style interface**: a cream diagonal-striped background, a game carousel with springy white-bordered tiles, rounded pastel accents, a rounded typeface, and the classic top status bar with live clock, Wi-Fi and battery indicators.

It is a fork of [Omnidroid](https://github.com/Ahmed-Abousaif/Omnidroid) by Ahmed Abousaif, which is itself a fork of [Lemuroid](https://github.com/Swordfish90/Lemuroid) by Filippo Scognamiglio — all GPLv3, all credit to them. ❤️

---

## 📸 Screenshots

<p align="center">
  <img src="docs/screenshots/home_carousel.png?v=2" alt="3DS-style home carousel" width="100%" />
</p>
<p align="center">
  <em>The 3DS Home view: a horizontal game carousel — the centered tile scales up with a white border, and the game title appears beneath the row</em>
</p>

<table align="center" width="100%">
  <tr>
    <td width="50%" align="center" valign="middle">
      <img src="docs/screenshots/library_grid.png?v=2" alt="Library grid view" width="100%" /><br />
      <b>Classic Grid View</b><br />
      <sub>Switch between 3DS Home, Grid and List at the tap of a button</sub>
    </td>
    <td width="50%" align="center" valign="middle">
      <img src="docs/screenshots/console_management.png?v=2" alt="Console carousel" width="100%" /><br />
      <b>Console Carousel</b><br />
      <sub>On-demand Libretro cores & database slice downloads</sub>
    </td>
  </tr>
  <tr>
    <td width="50%" align="center" valign="middle">
      <img src="docs/screenshots/settings.png?v=2" alt="Settings" width="100%" /><br />
      <b>Settings Hub</b><br />
      <sub>White rounded panels with pastel accents throughout</sub>
    </td>
    <td width="50%" align="center" valign="middle">
      <img src="docs/screenshots/gamer_profile.png?v=2" alt="Gamer profile" width="100%" /><br />
      <b>Gamer Profile & Stats</b><br />
      <sub>Play hours, daily streaks, XP leveling & session history</sub>
    </td>
  </tr>
</table>

---

## ✨ What's New in the 3DS Edition

- 🕹️ **3DS Home view (default):** a horizontal game carousel with spring-physics scale/bounce animations, selection borders, chevron navigation, full gamepad and touch support. Grid and List views remain one tap away.
- 🎨 **Classic 3DS light theme:** warm cream surfaces with a faint diagonal hatch, pastel green accents, rounded chips and white tiles across every screen — library, game details, settings, profile and the in-game menu.
- 🔤 **Rounded typography:** the whole app is set in [Nunito](https://fonts.google.com/specimen/Nunito) (OFL), echoing the friendly 3DS system font.
- 🧭 **Faithful top bar:** live clock, Wi-Fi, battery and controller indicators — the 3DS status row, already loved by Omnidroid users, now on a light canvas.

Everything else from Omnidroid is intact: Vulkan/GLES rendering, controller-first navigation, cloud saves, RAWG metadata, gamer profiles, and 20+ supported systems.

## 🕹️ Supported Consoles & Systems

| System                             | Console / Generation     | Emulation Core                                                                                                                                       |
| :--------------------------------- | :----------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Nintendo 3DS**                   | Handheld (8th Gen)       | [Azahar](https://github.com/azahar-emu/azahar) / [Citra](https://docs.libretro.com/library/citra/)                                                   |
| **Nintendo Wii U** _(Beta)_        | Home Console (8th Gen)   | [Cemu](https://github.com/cemu-project/Cemu)                                                                                                         |
| **Nintendo Wii** _(Beta)_          | Home Console (7th Gen)   | [Dolphin](https://docs.libretro.com/library/dolphin/)                                                                                                |
| **Nintendo GameCube**              | Home Console (6th Gen)   | [Dolphin](https://docs.libretro.com/library/dolphin/)                                                                                                |
| **Nintendo DS (NDS)**              | Handheld (7th Gen)       | [melonDS DS](https://github.com/JesseTG/melonds-ds)                                                                                                  |
| **Nintendo 64 (N64)**              | Home Console (5th Gen)   | [mupen64plus](https://docs.libretro.com/library/mupen64plus/)                                                                                        |
| **Game Boy Advance (GBA)**         | Handheld (6th Gen)       | [mgba](https://docs.libretro.com/library/mgba/)                                                                                                      |
| **Game Boy / Color (GB/GBC)**      | Handheld (4th/5th Gen)   | [gambatte](https://docs.libretro.com/library/gambatte/)                                                                                              |
| **Super Nintendo (SNES)**          | Home Console (4th Gen)   | [snes9x](https://docs.libretro.com/library/snes9x/)                                                                                                  |
| **NES / Famicom**                  | Home Console (3rd Gen)   | [fceumm](https://docs.libretro.com/library/fceumm/)                                                                                                  |
| **PlayStation 2 (PS2)** _(Beta)_   | Home Console (6th Gen)   | [ARMSX2](https://github.com/Ahmed-Abousaif/ARMSX2)                                                                                                   |
| **PlayStation Portable (PSP)**     | Handheld (7th Gen)       | [ppsspp](https://docs.libretro.com/library/ppsspp/)                                                                                                  |
| **PlayStation (PSX)**              | Home Console (5th Gen)   | [PCSX-ReARMed](https://docs.libretro.com/library/pcsx_rearmed/)                                                                                      |
| **Sega Dreamcast**                 | Home Console (6th Gen)   | [flycast](https://docs.libretro.com/library/flycast/)                                                                                                |
| **Sega Genesis / Mega Drive**      | Home Console (4th Gen)   | [genesis_plus_gx](https://docs.libretro.com/library/genesis_plus_gx/)                                                                                |
| **Sega CD / Mega CD**              | Add-on (4th Gen)         | [genesis_plus_gx](https://docs.libretro.com/library/genesis_plus_gx/)                                                                                |
| **Sega Master System / Game Gear** | 8-bit Console & Handheld | [genesis_plus_gx](https://docs.libretro.com/library/genesis_plus_gx/)                                                                                |
| **Arcade**                         | Classic Coin-Op Arcade   | [FBNeo](https://github.com/libretro/FBNeo/)                                                                                                          |
| **PC Engine / TurboGrafx-16**      | Home Console (4th Gen)   | [beetle_pce_fast](https://docs.libretro.com/library/beetle_pce_fast/)                                                                                |
| **Neo Geo Pocket / Color**         | Handheld (5th/6th Gen)   | [mednafen_ngp](https://docs.libretro.com/library/beetle_neopop/)                                                                                     |
| **WonderSwan / Color**             | Handheld (5th/6th Gen)   | [beetle_cygne](https://docs.libretro.com/library/beetle_cygne/)                                                                                      |
| **Atari 2600 / 7800 / Lynx**       | Classic Atari Family     | [stella](https://docs.libretro.com/library/stella/) / [prosystem](https://docs.libretro.com/library/prosystem/) / [handy](https://docs.libretro.com/library/handy/)                  |

## 🎮 Getting Started

1. Download the latest APK from the [Releases](https://github.com/bvnkfatboy/Omnidroid/releases) page (pick `arm64-v8a` for most modern devices, `universal` for anything else) and install it — Android 7.0+ required.
2. Point Omnidroid at the folder containing your ROMs (`.zip`, `.7z` and `.chd` supported). Cores and cover art download automatically on demand.
3. Optional: plug in a Bluetooth/USB gamepad, enable cloud save sync (Google Drive / Dropbox / OneDrive), and add a free [RAWG](https://rawg.io/apidocs) API key under **Settings → Advanced** for rich metadata and trailers.
4. Swipe through your library 3DS-style and tap a tile to play. Long-press for per-game options.

> 🎨 Prefer the old look? Switch between **3DS Home / Grid / List** with the toggle at the bottom-right of the library.

## 🛠️ Building from Source

```bash
git clone https://github.com/bvnkfatboy/Omnidroid.git
cd Omnidroid
git submodule update --init omnidroid-chd/src/main/cpp/libchdr   # CHD support (required)

# Debug APK
./gradlew :omnidroid-app:assembleFreeBundleDebug

# Signed release APK (uses release.jks + omnidroid.storePassword in local.properties if present,
# otherwise falls back to debug signing)
./gradlew :omnidroid-app:assembleFreeBundleRelease
```

Requirements: JDK 17+, Android SDK (compileSdk 36, build-tools 36.0.0), NDK + CMake. Without the `omnidroid-cores` submodule the build still succeeds — emulation cores are simply downloaded at runtime instead of being bundled.

---

## 🙏 Credits & Acknowledgements

This project stands on the shoulders of giants:

- **[Filippo Scognamiglio (Swordfish90)](https://github.com/Swordfish90)** — creator of [Lemuroid](https://github.com/Swordfish90/Lemuroid) and [LibretroDroid](https://github.com/Swordfish90/LibretroDroid), the foundation everything here is built on.
- **[Ahmed Abousaif](https://github.com/Ahmed-Abousaif)** — creator of [Omnidroid](https://github.com/Ahmed-Abousaif/Omnidroid), the landscape-first Lemuroid fork with Vulkan graphics API switching, RAWG metadata, cloud saves and the gamer profile system that this project builds upon.
- **The [libretro](https://www.libretro.com) team** and every core author — the decades of emulation work that make any of this possible.
- **The Retrograde project** — the original Android Libretro frontend Lemuroid descended from.
- The 3DS Edition UI is an affectionate homage to the Nintendo 3DS HOME Menu. Not affiliated with Nintendo. "Nintendo 3DS" is a trademark of Nintendo.

## 📄 License

Omnidroid — © 2020 Filippo Scognamiglio, 2024 Ahmed Abousaif, 2026 [bvnkfatboy](https://github.com/bvnkfatboy) (3DS Edition changes)

This program is free software: you can redistribute it and/or modify it under the terms of the [GNU General Public License v3](./COPYING). The Nunito font is used under the [SIL Open Font License](https://openfontlicense.org).
