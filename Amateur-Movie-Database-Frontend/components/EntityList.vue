<template>
  <div class="entity-container">
    <search-bar @update="updateList" />
    <div class="entity-list-container">
      <entity-list-item v-for="entity in entities" :key="entity.id" :entity="entity" />
    </div>
  </div>
</template>

<script>
import EntityListItem from '~/components/EntityListItem.vue'
import SearchBar from '~/components/SearchBar.vue'

export default {
  components: {
    EntityListItem,
    SearchBar
  },
  props: {
    entities: {
      type: Array,
      default () {
        return []
      }
    }
  },

  data () {
    return {
      searchQuery: ''
    }
  },

  methods: {
    updateList (data) {
      this.entities = []
      data.forEach((entity) => {
        this.entities.push(entity)
      })
    }
  }
}
</script>

<style lang="scss">

.entity-container {
  width: 600px;
  margin: auto;
  margin-top: 75px;
  display: block;
  padding-bottom: 20px;
}

.entity-list-container {
  width: 500px;
  margin: auto;
  margin-top: 50px;
  display: block;
}

</style>
