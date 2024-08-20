# elasitcsearch에서 활용하는 명령어

### index 조회
```shell
http://localhost:9200/_cat/indices?v
```

### index 매핑 정보 확인
```shell
http://localhost:9200/{INDEX_NAME}/_mapping?pretty
```
### article 조회
```shell
curl -X GET "http://localhost:9200/articles/_search" -H 'Content-Type: application/json' -d'
{
  "query": {
    "nested": {
      "path": "categories",
      "query": {
        "bool": {
          "must": [
            {
              "term": {
                "categories.code": "264"
              }
            }
          ]
        }
      }
    }
  }
}'
```
