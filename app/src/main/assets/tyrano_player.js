const TyranoPlayer = (function () {
  // コンストラクタ
  function TyranoPlayer(storage_url) {
    if (!(this instanceof TyranoPlayer)) {
      return new TyranoPlayer(storage_url)
    }
    this.storage_url = storage_url
  }

  TyranoPlayer.prototype.pauseAllAudio = function () {
    //tyranoのすべてのaudioを停止する。
    const bgm_objs = TYRANO.kag.tmp.map_bgm
    const se_objs = TYRANO.kag.tmp.map_se

    Object.values(bgm_objs).forEach((bgm) => bgm.pause())
    Object.values(se_objs).forEach((se) => se.pause())
  }

  TyranoPlayer.prototype.resumeAllAudio = function () {
    //tyranoのすべてのaudioを再開する。
    const bgm_objs = TYRANO.kag.tmp.map_bgm

    if (!TYRANO.kag.stat.current_bgm) return

    const currentBgm = bgm_objs[TYRANO.kag.stat.current_bgm] || bgm_objs[0]
    if (currentBgm) currentBgm.play()
  }
  return TyranoPlayer
})()

//ティラノプレイヤーの定義
var _tyrano_player = new TyranoPlayer('')

//セーブデータ
$.setStorage = function (key, val) {
  if ('appJsInterface' in window) {
    appJsInterface.setStorage(key, escape(JSON.stringify(val)))
  }
}

$.getStorage = function (key) {
  //とりあえず、データは保存する
  if ('appJsInterface' in window) {
    try {
      const json_str = appJsInterface.getStorage(key)
      if (json_str == '') return null
      return unescape(json_str)
    } catch (e) {
      console.log(e)
    }
  }
}
