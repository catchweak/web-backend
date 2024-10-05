#!/bin/bash

# 인덱스 생성
curl -X PUT "http://localhost:9200/articles" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "categories": {
        "type": "nested",
        "properties": {
          "id": { "type": "long" },
          "name": { "type": "text" },
          "code": { "type": "keyword" },
          "parentCode": { "type": "keyword" },
          "siteId": { "type": "long" },
          "siteName": { "type": "text" }
        }
      },
      "url": { "type": "keyword" },
      "origin_url": { "type": "keyword" },
      "headline": { "type": "text" },
      "body": { "type": "text" },
      "img_url": { "type": "keyword" },
      "summary": { "type": "text" },
      "author": { "type": "keyword" },
      "article_created_at": { "type": "date", "format": "yyyy-MM-dd HH:mm:ss" },
      "article_updated_at": { "type": "date", "format": "yyyy-MM-dd HH:mm:ss" },
      "collected_at": { "type": "date", "format": "yyyy-MM-dd HH:mm:ss" }
    }
  }
}'

curl -X PUT "http://localhost:9200/article_keywords" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "analysis": {
      "analyzer": {
        "comma_analyzer": {
          "type": "custom",
          "tokenizer": "comma_tokenizer"
        }
      },
      "tokenizer": {
        "comma_tokenizer": {
          "type": "pattern",
          "pattern": ","
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "article_id": { "type": "long" },
      "keywords": {
        "type": "text",
        "analyzer": "comma_analyzer"
      }
    }
  }
}'

curl -X PUT "http://localhost:9200/article_summaries" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "article_id": { "type": "long" },
      "summary": { "type": "text" }
    }
  }
}'

