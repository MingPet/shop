<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> -->
   
    <input type="button" value="品牌管理">
    <input type="button" value="商品管理">
    <router-view></router-view>

    <input type="text" v-model="brand.id"/>
    <input type="text" v-model="brand.brandName"/>
    <BrandList :brands="brands1" brandColor="red" @fhEvent="showInfo"></BrandList>
    <BrandList :brands="brands2" brandColor="blue" @fhEvent="showInfo"></BrandList>
    
  </div>
</template>

<script>
//import HelloWorld from './components/HelloWorld'
import BrandList from './view/BrandList'


export default {
  name: 'App',
  components: {
    
    BrandList
    
  },
  data() {
    return {
      // color1:"red",
      // color2:"blue",
      brand:{},
      brands1:[
        {id:1,"brandName":"青啤"},
        {id:2,"brandName":"雪啤"},
        {id:3,"brandName":"哈啤"},
      ],
       brands2:[
        {id:4,"brandName":"百威"},
        {id:5,"brandName":"科罗娜"},
        {id:6,"brandName":"燕京"},
      ]
    }
  },
  methods: {
    showInfo(v){
      this.brand = v;
    }
  },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
