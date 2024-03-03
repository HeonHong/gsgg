const menus = [
  {
    path: "errorPage",
    name: "ErrorPage",
    componentPath: "ErrorPage",
  },
  {
    path: "test",
    name: "TestPage",
    componentPath: "test/TestPage",
  },
  // 케릭터 소개 페이지
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
];

export default menus;