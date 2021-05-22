<template>
  <div class="container">
    <div class="title-container">
      <h1 class="title">
        AMDb
      </h1>
    </div>
    <entity-list :entities="entities" />
  </div>
</template>

<script>
import EntityList from '~/components/EntityList.vue'

export default {
  components: {
    EntityList
  },

  asyncData () {
    return {
      entities: [
      ]
    }
  },

  data () {
    return {
      entities: {
        type: Array
      }
    }
  }
}
</script>

<style>

.title-container {
  position: relative;
  top: 30px;
  background: #ff471a;
  width: 400px;
  border-radius: 20px;
  display: block;
  margin: auto;
  box-shadow: 10px 12px #1a1a1a;
}

</style>
