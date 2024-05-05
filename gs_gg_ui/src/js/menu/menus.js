const menus = [
  {
    path: "errorPage",
    name: "ErrorPage",
    componentPath: "ErrorPage",
  },
  // 캐릭터 소개 페이지
  {
    path: "characters",
    name: "CharactersPage",
    componentPath: "characters/CharactersPage",
  },
  {
    path: "",//내가 브라우저에서 검색하고 싶은 주소
    name: "RecordsPage",//컴포넌트 이름
    componentPath: "records/RecordsPage",//실제 주소
  },
  {
    path: "userVs",
    name: "UserVsPage",
    componentPath: "uservs/UserVsPage",
  },
  // 테스트 페이지 관련
  {
    path: "test",
    name: "TestPage",
    componentPath: "test/TestPage",
  },
  {
    path: "sockettest",
    name: "SocketTest",
    componentPath: "test/SocketTest",

  },
  {
    path: "login",
    name: "LoginPage",
    componentPath: "login/LoginPage",
  },
  {
    path: "join",
    name: "JoinPage",
    componentPath: "login/JoinPage",
  },

];

export default menus;