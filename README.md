# StandBlock HUB (Remake)

[![StandBlock Remake](https://i.imgur.com/Z1rT0nR.jpg)]()

[![Author](https://img.shields.io/badge/author-MrLizzard-orange.svg)](https://twitter.com/ColinetCyril)
[![Gitter](https://img.shields.io/gitter/room/nwjs/nw.js.svg)](https://gitter.im/standblock-remake-one-year-later/)
[![Build Status](https://travis-ci.org/StandBlock-REMAKE/hub.svg?branch=master)](https://travis-ci.org/StandBlock-REMAKE/hub)
[![GitHub license](https://img.shields.io/badge/license-AGPL-blue.svg)](https://raw.githubusercontent.com/StandBlock-REMAKE/hub/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/StandBlock-REMAKE/hub.svg)](https://github.com/StandBlock-REMAKE/hub/stargazers)
[![Maintenance](https://img.shields.io/badge/maintained-no-red.svg)]()

StandBlock et une grande partie du staff, ont décidé de, en mémoire à ce projet qui a prit tant de temps à se mettre en place, avec plus de 2 années de développement consécutives (plutôt intensives qu'on se le dise), d'organiser un évènement entièrement public disponible à tous, aux anciens inscrits comme aux personne n'ayant jamais entendu parlé ou n'ayant jamais trempé le petit orteil sur nos serveurs. Et en prime, j'ai décidé en tant que développeur principal du projet, de refaire tous les anciens plugins, avec les jeux et tous leurs fonctions, et de les mettre en projet sur Github pour faire profiter à tout le monde de ce (ancien) projet complètement majestueux.

> Information IMPORTANTE: Ce projet étant terminé, aucune assistance ni aide ne sera fournie par moi et les développeurs qui ont participé à ce développement. Merci de ne pas nous harceler de messages pour dire "ça marche pas, aide moi". Nous vous en serons fortement reconnaissants.

## Personnes ayant participées à sa conception

- MrLizzard **(développeur principal & chef de projet)**
- Bestaylex **(chef de projet)**

## Informations sur les modifications

Il vous est entièrement possible de modifier les sources et d'y apporter des modifications à votre guise. Les sources diffusées sont uniquement présentes pour tous les développeurs débutants qui souhaitent apprendre à maîtriser Java avec des APIs telles que Spigot (PaperSpigot dans notre cas) et BungeeCord (Waterfall dans notre cas). Vous pouvez tout aussi bien reprendre les idées et/ou les concepts présents dans ces plugins pour les incorporer à votre network.

## Requirements

- Java 8 (JDK)
- Maven
- Spigot 1.8.8 (PaperSpigot)
- MySQL

## Installation

Afin de pouvoir installer le plugin, il vous suffit de cloner ce repo `git clone https://github.com/StandBlock-REMAKE/hub.git` sur votre ordinateur et suivre les étapes suivantes.

### Compiler le plugin

Pour compiler le plugin, rendez-vous dans le dossier où se trouve les fichiers, ouvrez ensuite une invite de commande et éxécutez la commande suivante
```
mvn clean install
```
Une fois le plugin correctement compilé, rendez-vous dans le dossier `/target` fraîchement créé, récupérez le fichier se terminant par `-jar-with-dependencies.jar` et glissez-le dans votre dossier plugins. Lancez le serveur et éteignez-le une fois complètement lancé.

### Configuration

Voici la présentation et la configuration de base du plugin. Vous devez modifier les accès SQL.
```
## Core configuration
core:
  debug-mode: true

## Database connector
mysql:
  host: 127.0.0.1
  database: standblock-remake-event
  username: root
  password: root
```

## Licence

Licence GNU General Public Licence v3.0
