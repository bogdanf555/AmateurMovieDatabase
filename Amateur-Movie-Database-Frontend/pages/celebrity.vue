<template>
  <div class="wrapper-container">
    <div class="menu-bar">
      <button class="search-button" @click="redirect">
        AMDb
      </button>
    </div>
    <div class="content-body">
      <div class="left-blank">
      </div>
      <div class="content">
        <div class="celebrity-bar">
          <h1 id="title">
            {{ celebrity.name }}
          </h1>
        </div>
        <div class="celebrity-content">
          <img class="poster" :src="celebrity.poster ? celebrity.poster: '/blank_person.jpg'" alt="poster">
          <p v-show="celebrity.description" id="description" class="celebrity-text">
            <b>{{ celebrity.description != null ? 'Description : ': '' }} </b>
            {{ celebrity.description != null ? celebrity.description : '' }}
          </p>
          <p v-show="celebrity.birth || celebrity.death" id="info" class="celebrity-text">
            <b v-show="celebrity.birth">Born: {{ celebrity.birth ? celebrity.birth : '-' }}</b>
            <span v-show="celebrity.birth && celebrity.death"> | </span>
            <b v-show="celebrity.death">
              Died: {{ celebrity.death ? celebrity.death : '-' }}
            </b>
          </p>
          <p id="professions" class="celebrity-text">
            <b>Professions: | </b>
            <span v-for="profession in celebrity.professions" :key="profession">
              {{ profession }} |
            </span>
          </p>
          <p id="knownFor" class="celebrity-text">
            <b>Known For: | </b>
            <span v-for="title in celebrity.knownFor" :key="title.id">
              <nuxt-link :to="{ name: 'title', params: { id: title.id }}">
                {{ title.name }}
              </nuxt-link> | </span>
          </p>
        </div>
      </div>
      <div class="right-blank">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      celebrity: {
      }
    }
  },
  created () {
    this.$services.celebrity.findById(this.$route.params.id).then((data) => {
      this.celebrity = data
    })
  },

  methods: {
    redirect () {
      this.$router.push('/')
    }
  }
}
</script>

<style lang=scss>

.wrapper-container {
  margin: 0;
  min-height: 100vh;
  display: grid;
  grid-template: 'menu'
                 'content';
  grid-template-rows: 7% auto;
}

.menu-bar {
  grid-area: menu;
  background: #00264d;
  padding: 5px;
}

.search-button {
  font-size: 28px;
  color: snow;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  text-align: center;
  padding: 5px;
  box-shadow: 4px 5px #1a1a1a;
  background: #ff471a;
  margin-left: 25px;
  &:focus {
    outline: none;
  }
}

.content-body {
  grid-area: content;
  background: gray;
  display: grid;
  grid-template: 'left-blank content right-blank';
  grid-template-columns: 15% 50% auto;
}

.content {
  grid-area: content;
  display: grid;
  grid-template: 'title-bar' 'title-content';
  grid-template-rows: 10% auto;
  grid-gap: 2%;
  background: gray;
}

.celebrity-bar {
  grid-area: title-bar;
  background: #000d1a;
  color: white;
  text-align: center;
  padding: 10px;
  box-shadow: dimgray 0 5px;
}

.celebrity-content {
  grid-area: title-content;
  background: gray;
  padding: 2%
}

.poster {
  background: slateblue;
  width: 200px;
  float: left;
  margin-top: 10px;
  border: darkgrey 2px;
  box-shadow: dimgray 7px 10px;
}

.celebrity-text {
  padding: 10px;
  margin-top: 10px;
  margin-left: 250px;
  background: snow;
  box-shadow: dimgray 5px 6px;
}

.left-blank {
  grid-area: left-blank;
  background: snow;
}

.right-blank {
  grid-area: right-blank;
  background: snow;
}

</style>
