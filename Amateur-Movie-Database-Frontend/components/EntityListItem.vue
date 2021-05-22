<template>
  <div class="entity-item">
    <img :src="entity.poster" class="poster-item">
    <nuxt-link
      id="title-link"
      :to="{ name: entity.id.startsWith('tt') ? 'title': 'celebrity', params: { id: entity.id } }"
    >
      {{ entity.title }} ({{ entity.year }})
    </nuxt-link>
  </div>
</template>

<script>
export default {
  props: {
    entity: {
      type: Object,
      default () {
        return {}
      }
    }
  }
}
</script>

<style lang="scss">
.entity-item {
  width: 100%;
  display: block;
  height: 150px;
  margin: auto;
  margin-top: 20px;
  background: white;
  border-radius: 20px;
  box-shadow: 7px 10px rgba(0, 0, 0, 0.3);
}

.poster-item {

  height: 90%;
  margin-top: 10px;
  width: 115px;
  float: left;
  padding-left: 20px;
}

#title-link {
  height: 100px;
  padding-left: 20px;
  padding-top: 50px;
  width: 300px;
  display: inline-block;
  vertical-align: center;
  text-decoration: none;
  color: black;
  font-weight: bold;
  font-size: 20px;
}
</style>
