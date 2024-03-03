// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true,
// })
module.exports = {
  devServer: {
    //   proxy: {
    //       '/api': {
    //           target: 'http://localhost:8080',
    //           changeOrigin: true,
    //           pathRewrite: {
    //               '^/': ''
    //           }
    //       }
    //   }

    //.env.local >> 로컬 설정. 추후 배포하고 prd 설정 필요
    proxy: process.env.VUE_APP_API_BASE_URL
  }
}