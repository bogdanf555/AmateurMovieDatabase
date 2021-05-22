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
        <div class="title-bar">
          <h1 id="title">
            {{ title.name }} {{ title.year ? '(' + title.year + ')' : '' }}
          </h1>
        </div>
        <div class="title-content">
          <img class="poster" :src="title.poster? title.poster: '/film.jpg'" alt="poster">
          <p id="info" class="title-text">
            <b>Duration: {{ title.duration ? title.duration + 'm' : '-' }} </b>
            <span> | </span>
            <b>
              Genres:
              <b v-for="genre in title.genres" :key="genre">
                {{ genre }}
              </b>
              {{ title.genres == null ? '-': '' }}
            </b>
          </p>
          <p v-show="title.plot" id="plot" class="title-text">
            <b>{{ title.plot != null? 'Plot:': '' }} </b>
            {{ title.plot != null? title.plot: '' }}
          </p>
          <p id="directors" class="title-text">
            <b>Directors: | </b>
            <span v-for="director in title.directors" :key="director.id">
              <nuxt-link :to="{ name: 'celebrity', params: { id: director.id }}">
                {{ director.fullName }}
              </nuxt-link> | </span>
          </p>
          <p id="writers" class="title-text">
            <b>Writers: | </b>
            <span v-for="writer in title.writers" :key="writer.id">
              <nuxt-link :to="{ name: 'celebrity', params: { id: writer.id }}">
                {{ writer.fullName }}
              </nuxt-link> | </span>
          </p>
          <p id="stars" class="title-text">
            <b>Stars: | </b>
            <span v-for="star in title.stars" :key="star.id">
              <nuxt-link :to="{ name: 'celebrity', params: { id: star.id }}">
                {{ star.fullName }}
              </nuxt-link> | </span>
          </p>
          <p id="ratings" class="title-text">
            <b> IMDb Rating:
              <a :href="'https://www.imdb.com/title/' + title.id">{{ title.rating ? title.rating : '-' }}</a>
            </b>
            <span> | </span>
            <b> Voted by: {{ title.votes ? title.votes: '-' }}</b>
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
      title: {
      }
    }
  },
  created () {
    this.$services.title.findById(this.$route.params.id).then((data) => {
      this.title = data
    })
  },

  methods: {
    redirect () {
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss">

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

.title-bar {
  grid-area: title-bar;
  background: #000d1a;
  color: white;
  text-align: center;
  padding: 10px;
  box-shadow: dimgray 0 5px;
}

.title-content {
  grid-area: title-content;
  background: gray;
  padding: 2%
}

.poster {
  background: slateblue;
  width: 225px;
  float: left;
  margin-top: 10px;
  border: darkgrey 2px;
  box-shadow: dimgray 7px 10px;
}

.title-text {
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
