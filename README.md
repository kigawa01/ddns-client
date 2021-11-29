# DDNSClient

DDNSのクライアント

## Requirement


# Author

* kigawa
    * kigawa.8390@gmail.com


## About

* 手動でAレコードを更新した場合不具合が発生する場合があります
* 対応DDNS
  * cloudflare
* 現在はcloudflareのAレコードの更新のみ対応している

## Command

* end
  * 終了する
* test
  * dnsのAレコードを更新する

## Config

* EMail
  * 認証に使用するEmail
* domain
  * 更新するドメイン
* id
  * レコードのid
* key
  * APIのtoken
* name
  * 内部的なもの
* zoneId
  * zoneId

## Change



# making

## Version

### 例: 9.1a

* **9.1dev**
  * プラグインのバージョン
  * **9**: メジャー
  * **1**: マイナー
  * **dev**: プラグインのバージョン バグがあるかもしれない(dev)/致命的なバグがないと思われる(b)/バグはないと思われる(R)

## TODO

* [ ] Loggerを改善する

## sample

