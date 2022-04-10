# IT-NOTES

## Table of Contents

1. [General info](#general-info)
2. [Technologies](#technologies)
3. [Setup](#setup)

<a name="general-info"></a>
### General info
IT-NOTES is a web application written in Scala and Play framework. The app present knowledge from IT in form, of concise articles and quizzes. It is created for practise and combining technologies, which I use in my current job. To present correct distributed system mechanism, client and server are separated:
- forntend: https://github.com/AlarQ/it-notes-front - React app,
- backend: https://github.com/AlarQ/it-notes-back - Scala app in Cats ecosystem.

<a name="technologies"></a>
### Technologies
1. Scala
2. Play 
3. Sangria
4. Elastic4s
5. Circe
6. Quicklens

<a name="setup"></a>
### Setup
App can be set up using docker-compose file added to project with command:

<code>docker-compose.yml up -d</code>
