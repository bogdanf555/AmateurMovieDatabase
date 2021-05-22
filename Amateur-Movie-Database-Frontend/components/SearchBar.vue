<template>
  <div class="search-container">
    <select v-model="searchEntity" class="dropdown">
      <option value="title" selected>
        Titles
      </option>
      <option value="celebrity">
        Celebrities
      </option>
    </select>
    <input v-model="searchQuery" @keyup.enter="search" placeholder="Search" type="text">
    <button @click="search">
      🔍 Search
    </button>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchQuery: '',
      searchEntity: 'title'
    }
  },

  methods: {
    search () {
      if (this.searchEntity === 'title') {
        this.$services.title.findByName(this.searchQuery).then((data) => {
          this.$emit('update', data)
          this.searchQuery = ''
        })
      } else if (this.searchEntity === 'celebrity') {
        this.$services.celebrity.findByName(this.searchQuery).then((data) => {
          this.$emit('update', data)
          this.searchQuery = ''
        })
      }
    }
  }
}
</script>

<style lang="scss">
.search-container {
  background: #000d1a;
  border-radius: 20px;
  width: 100%;
  height: 51px;
  box-shadow: 5px 10px rgba(0, 0, 0, 0.3);

  input {
    border-radius: 20px;
    height: 40px;
    margin-left: 10px;
    margin-top: 5px;
    margin-bottom: 5px;
    width: 330px;
    border: none;
    padding-left: 20px;
    font-family: "Open Sans", -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
      Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    font-size: 24px;
    &:focus {
      outline: none;
    }
  }

  select {
    border-radius: 20px;
    float: left;
    height: 40px;
    width: auto;
    border: none;
    margin-left: 10px;
    margin-top: 5px;
    margin-bottom: 5px;
    padding-left: 12px;
    padding-right: 10px;
    font-family: "Open Sans", -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
      Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    font-size: 18px;
    &:focus {
      outline: none;
    }
  }

  button {
    background: white;
    border-radius: 20px;
    height: 40px;
    width: auto;
    float: right;
    margin-right: 10px;
    margin-top: 5px;
    margin-bottom: 5px;
    cursor: pointer;
    border: none;
    text-align: center;
    font-size: 18px;
    padding-right: 10px;
    &:focus {
      outline: none;
    }
  }
}
</style>
