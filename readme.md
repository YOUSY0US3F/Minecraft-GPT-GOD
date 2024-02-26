# GPT GOD for Minecraft
# Discalimer
This repo does not contain and will never contain a functioning product, see [GPT god plugin](https://github.com/YOUSY0US3F/minecraft-gpt-god-plugin) instead
## Helpful resources

- [Simple Voice Chat Plugin API](https://github.com/henkelmax/simple-voice-chat/blob/1.18.2/api/readme.md) for Forge.
- [Forge Docs](https://docs.minecraftforge.net/en/latest/gettingstarted/)
- [Vosk Java demo](https://github.com/alphacep/vosk-api/blob/master/java/demo/src/main/java/org/vosk/demo/DecoderDemo.java)

## Local setup

- clone the repo
- If you are using VSCode (like me):
- Install gradle for VSCode plugin
- Open the repo with VSCode
- at this point the plugin should setup the environment
- run `./gradlew` in your terminal
- If you aren't using VSCode please refer to the [Forge Docs](https://docs.minecraftforge.net/en/latest/gettingstarted/)

## Local Testing

- run the runServer task either `gradle tab-> Tasks-> forgegradle runs-> runServer` or `./gradlew runServer`
- this will fail
- in your run folder the will be a file `eula.txt`
- set `eula=true`
- go into `server.properties`
- set `online-mode=false`
- now when you run the server you can connect to it with the client by running the runClient task or `./gradlew runClient`
