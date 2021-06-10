<template>
  <div>
     <table border="1px" cellpadding="0px" cellspacing="0px" width="500px" 
     :style="{color:brandColor}">
         <tr>
             <th>编号</th>
             <th>品牌名</th>
             <th>操作</th>
         </tr>
         <tr v-for="brand in brands">
             <td>
                 {{brand.id}}
             </td>
             <td>
                 {{brand.brandName}}
             </td>
             <td>
                 <input type="button" value="选择" @click="showData(brand.id)">
             </td>
         </tr>
     </table>
  </div>
</template>

<script>
export default {
    props:["brands","brandColor"],
    methods: {
        showData(id){
            var v_brand = this.brands[this.brands.findIndex(item => item.id==id)];
            this.$emit("fhEvent",v_brand);//子组件发送一个 fhEvent
        }
    },
}
</script>

<style>

</style>