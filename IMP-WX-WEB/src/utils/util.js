export const global = {
  userInfo:'',
  impWxid:''
};
export function setCookie(key, value) {
  let argv = arguments;
  let argc = arguments.length;
  let path = (argc > 3) ? argv[3] : null;
  let domain = (argc > 4) ? argv[4] : null;
  let secure = (argc > 5) ? argv[5] : false;

  // let currentTime = new Date();
  // currentTime.setTime(currentTime.getTime() + (24 * 60 * 60 * 1000));
  // let expires = currentTime;
  let expires = null;
  document.cookie = key + "=" + encodeURI(value)
    + ((expires == null) ? "" : ("; expires=" + expires.toGMTString()))
    + ((path == null) ? "" : ("; path=" + path))
    + ((domain == null) ? "" : ("; domain=" + domain))
    + ((secure == true) ? "; secure" : "");
}

export function saveSession(value) {
  setCookie('impWx-jsid', value);
}

export function getSession() {
  let key = 'impWx-jsid'
  let arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
　return (arr=document.cookie.match(reg))?unescape(arr[2]):null;
}

export function deleteSession() {
  deleteCookie('impWx-jsid')
}

export function deleteCookie(key) {

  let argv = arguments;
  let argc = arguments.length;
  let path = (argc > 3) ? argv[3] : null;
  let domain = (argc > 4) ? argv[4] : null;
  let secure = (argc > 5) ? argv[5] : false;

  let currentTime = new Date();
  currentTime.setTime(currentTime.getTime() - 1);
  let expires = currentTime;
  document.cookie = key + "=" + encodeURI(null)
    + ((expires == null) ? "" : ("; expires=" + expires.toGMTString()))
    + ((path == null) ? "" : ("; path=" + path))
    + ((domain == null) ? "" : ("; domain=" + domain))
    + ((secure == true) ? "; secure" : "");
}

