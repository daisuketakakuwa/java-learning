# TroubleShooting メモ

## Logstash 　 → Elasticsearch へ疎通させる

### 1. 同じネットワーク内に各コンテナを配置する。

Network を作成。

```
docker network create elasticstack

```

該当コンテナを実行するネットワークを`docker-compose.yml`内で指定。

```yml
networks:
  - elasticstack
```

2. Logstash と Elasticsearch の設定ファイル編集

## Logstash 関連の設定ファイル

コンテナ内にログインして、Default 設定ファイルを取得。<br>
`/usr/share/logstash`配下のファイルを見る。

```
docker exec -it policy-api_logstash_1 /bin/bash
```

## Elasticsearch 関連の設定ファイル

コンテナ内にログインして、Default 設定ファイルを参照<br>
`/usr/share/elasticsearch/config/elasticsearch.yml`を見る。

```
docker exec -it policy-api_elasticsearch_1 /bin/bash
```
