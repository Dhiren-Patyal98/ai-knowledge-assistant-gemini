# AI Knowledge Assistant

A Spring Boot based AI-powered Knowledge Assistant that allows authenticated users to upload documents, generates vector embeddings, stores them in PostgreSQL using pgvector, and answers questions using Retrieval-Augmented Generation (RAG) with Google Gemini.

---

## Features

- JWT Authentication
- Secure document upload
- PDF/Text document parsing using Apache Tika
- Automatic text chunking
- Vector embedding generation using Google Gemini
- PostgreSQL + pgvector for vector storage
- Semantic similarity search
- Retrieval-Augmented Generation (RAG)
- Google Gemini for conversational responses
- User-isolated document retrieval

---

## Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring AI 2.0
- Spring Security
- Spring Data JPA

### AI

- Google Gemini
    - Gemini 3.5 Flash (Chat)
    - Gemini Embedding 001 (Embeddings)

### Database

- PostgreSQL
- pgvector

### Document Processing

- Apache Tika

### Authentication

- JWT

---

## Architecture

```
             Upload Document
                    │
                    ▼
             Apache Tika Parser
                    │
                    ▼
             Text Chunking
                    │
                    ▼
       Google Embedding Model
                    │
                    ▼
          PostgreSQL + pgvector


User Question
      │
      ▼
Google Embedding Model
      │
      ▼
Similarity Search
      │
      ▼
Relevant Chunks
      │
      ▼
Prompt Builder
      │
      ▼
Google Gemini Chat Model
      │
      ▼
Final Answer
```

---

## Project Structure

```
src/main/java

├── auth
├── ai
│   ├── chat
│   ├── embedding
│   ├── retrieval
│   ├── prompt
│   └── service
├── document
├── security
├── common
└── config
```

---

## Configuration

Create an `.env` or configure your environment variables.

```
GEMINI_API_KEY=YOUR_API_KEY
```

application.properties

```properties
spring.ai.google.genai.api-key=${GEMINI_API_KEY}

spring.ai.google.genai.chat.options.model=gemini-3.5-flash

spring.ai.google.genai.embedding.options.model=gemini-embedding-001

spring.ai.model.embedding.text=google-genai
```

---

## Workflow

1. Register/Login
2. Upload documents
3. Document is parsed
4. Text is chunked
5. Embeddings are generated
6. Chunks are stored in PostgreSQL
7. Ask questions
8. Relevant chunks are retrieved
9. Gemini generates an answer using retrieved context

---

## APIs

### Authentication

```
POST /api/auth/register
POST /api/auth/login
```

### Documents

```
POST /api/documents/upload
```

### Chat

```
POST /api/chat/ask
```

---

## Retrieval Process

- User question is converted into an embedding.
- PostgreSQL pgvector performs cosine similarity search.
- Top K matching chunks are retrieved.
- Retrieved context is added to the prompt.
- Gemini generates the final answer.

---

## Future Enhancements

- Source citations
- Streaming responses
- Conversation history
- Multi-document references
- Hybrid search
- Redis caching
- Document deletion with embedding cleanup
- Admin dashboard

---

## Author

**Dhiren Patyal**

Java Backend Developer
