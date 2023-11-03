# java-learning

## HikariCP

- コネクションプーリングを担当するライブラリ。
- SpringBoot に組み込まれているだけで、ライブラリ単体でも利用可能。
- 細かい設定は[公式ドキュメント](https://github.com/brettwooldridge/HikariCP)参照 👍

## Connection Pooling

- SpringBoot アプリ起動時に設定した数のコネクションを生成＆プーリングしておく。
- 必要になったタイミングで各スレッドはプールされているコネクションを使う。
- プールから払い出せるコネクションがなくなったら、どれくらい待つか(`connection-timeout`)等でエラーになるタイミングを制御する。

### Persistent Pooling とは？

- Refer: [MySQL Connection Pooling と Persistent Connections はチョット違うという話](https://mita2db.hateblo.jp/entry/2020/08/02/162024).
- Worker スレッドごとに DB コネクションを生成するイメージ。
- Apache(Prefork) + PHP ではこのスタイルらしい。

## Service 層で Entity→Response へオブジェクト変換をする理由

- JPA の Entity クラスは使い方によっては想定外のところでクエリが発行されたりする。
- 一例として「Response に Entity オブジェクトをそのまま返すパターン」である。
- このパターンだと API がレスポンスを返すまでコネクションがその Worker によって専有されてしまう。
- **1 回のクエリで情報を取得し、コネクションをすぐにリリースする**ように実装する。

- 学校テーブル - 生徒テーブル をレスポンスとして返す場合で、
- 生徒テーブルを LAZY FETCH している場合は`getStudents()`したタイミングでやっとクエリが発行される。
- `spring.jpa.open-in-view`を false にする等して、**意図しないタイミングでクエリを**
