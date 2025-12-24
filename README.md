# PolarMC

Welkom! Dit is de officiële Minecraft server van PolarNL, veel plezier!

## IP
`mc.polarnl.org`

## Over PolarLobby Plugin

PolarLobby is een Minecraft Paper plugin ontworpen om een veilige en gecontroleerde lobby-ervaring te bieden. De plugin biedt verschillende functies om de lobby te beschermen en spelers te beheren.

### Hoofdfuncties

#### 1. Spawn Beheer
- Spelers worden automatisch geteleporteerd naar de ingestelde spawn locatie bij het joinen
- Spelers respawnen op de ingestelde spawn locatie
- Admins kunnen de spawn locatie instellen met `/pl setspawn`

#### 2. Bescherming Systemen
- **Anti-Damage**: Voorkomt dat spelers elkaar schade toebrengen (tenzij ze admin rechten hebben)
- **Honger Preventie**: Spelers krijgen geen honger in de lobby
- **Blok Bescherming**: Standaard kunnen spelers geen blokken plaatsen of breken
- **Interactie Blokkering**: Voorkomt interactie met kisten, deuren, knoppen en andere objecten

#### 3. Void Teleportatie
- Spelers die te ver naar beneden vallen (standaard Y < -64) worden automatisch terug geteleporteerd naar spawn
- Inclusief geluid en bericht notificatie

#### 4. Blok Break Toestemming Systeem
- Admins kunnen individuele spelers toestemming geven om blokken te plaatsen/breken
- Handig voor bouwen aan de lobby terwijl de server online is
- Gebruik `/pl allowbreak [speler]` om permissie te togglen

### Commando's
Alle commando's zijn beschikbaar via `/pl` of `/polarlobby`:
- `/pl setspawn` - Stelt de spawn locatie in op je huidige positie (admin)
- `/pl allowbreak [speler]` - Toggle blok plaatsen/breken permissie voor een speler (admin)
- `/pl voidtp` - Configureer de Y-waarde voor void teleportatie (admin)
- `/pl reload` - Herlaadt de plugin configuratie (admin)

### Configuratie
De plugin gebruikt `config.yml` voor instellingen:
- `allowBreak`: Of blokken standaard gebroken kunnen worden (false aanbevolen)
- `antidamage`: Of spelers beschermd zijn tegen schade (true aanbevolen)
- `spawn_coordinates`: Spawn locatie (gebruik `/pl setspawn` om in te stellen)
- `voidtp_y`: Y-coördinaat voor void teleportatie (standaard -64)
- `messages`: Aangepaste join/leave berichten

### Technische Details
- Geschreven in Kotlin voor Paper 1.21.11
- Vereist ProtocolLib als dependency
- Gebruikt Adventure API voor moderne text formatting met MiniMessage
- Java 21 vereist

## Handige links
- [Problemen](https://github.com/polarnl/PolarLobby/issues)
- [Discord](https://discord.gg/TdYzXDzqSB)

Product van PolarNL - Copyright 2025
