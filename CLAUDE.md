# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

```bash
# Run development server (hot-reload enabled)
sbt run

# Run all tests
sbt test

# Run a single test class
sbt "testOnly *HtmlConverterSpec"

# Format code with Scalafmt
sbt scalafmt

# Check formatting without modifying
sbt scalafmtCheck

# Start Docker environment (Elasticsearch cluster + Kibana)
docker-compose up -d

# Build Docker image
sbt docker:publishLocal
```

## Architecture Overview

IT-NOTES is a Scala/Play Framework backend for a knowledge management system. The frontend (React) lives in a separate repository.

### Layered Architecture

```
Controllers (Play MVC)
    ↓
GraphQL Server (Sangria)
    ↓
Mutations & Queries
    ↓
Resolvers → Loaders
    ↓
Elasticsearch Repository (Elastic4s)
```

### Key Directories

- `app/controllers/` - Play controllers, primarily `HomeController` handling all routes
- `app/graphql/` - Sangria GraphQL schema, queries, mutations, resolvers
- `app/elastic/` - Elasticsearch repository with read/write operations
- `app/model/` - Domain models (Article, QuizPosition) extending Entity trait
- `app/common/` - Utilities including HtmlConverter (markdown-like to HTML)

### Domain Entities

Two main entities stored in Elasticsearch:
- **Article** - IT knowledge articles with title, content, category, tags
- **QuizPosition** - Quiz questions with answers, repetition tracking, category

### GraphQL API

- Endpoint: `/graphql` (POST for queries/mutations, GET for GraphiQL interface)
- Schema defined in `app/graphql/schema/`
- Queries: `getArticles`, `getQuizPositions`
- Mutations: create/delete operations for both entities

### HtmlConverter Syntax

The `HtmlConverter` transforms custom markdown-like syntax to HTML:
- Code blocks: `<|code|>` → `<pre><code>...</code></pre>`
- Lists: `l#E>item#l` → `<ul><li>item</li></ul>`

## Tech Stack

- Scala 2.13.6 / Play Framework 2.8.8
- Sangria (GraphQL)
- Elastic4s (Elasticsearch 7.8.1 client)
- Circe (JSON)
- Akka Streams
- ScalaTest with scalatestplus-play

## Code Style

Scalafmt configured with:
- Max line length: 120 characters
- Import sorting enabled
- Trailing commas preserved
