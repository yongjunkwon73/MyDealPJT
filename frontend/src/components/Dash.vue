<template>

<v-data-table
    :headers="headers"
    :items="dash"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'Dash',
    props: {
      value: Object,
      editMode: Boolean,
      isNew: Boolean
    },
    data: () => ({
        headers: [
            { text: "dashId", value: "dashId" },
            { text: "puId", value: "puId" },
            { text: "cuId", value: "cuId" },
            { text: "puDate", value: "puDate" },
            { text: "puCancelDate", value: "puCancelDate" },
            { text: "puStatus", value: "puStatus" },
            { text: "payId", value: "payId" },
            { text: "payDate", value: "payDate" },
            { text: "payCancelDate", value: "payCancelDate" },
            { text: "payStatus", value: "payStatus" },
            { text: "conId", value: "conId" },
            { text: "conDate", value: "conDate" },
            { text: "conCancelDate", value: "conCancelDate" },
            { text: "conStatus", value: "conStatus" },
            { text: "stockId", value: "stockId" },
            { text: "stockType", value: "stockType" },
            { text: "stockDate", value: "stockDate" },
            { text: "stockTotal", value: "stockTotal" },
        ],
        dash : [],
    }),
    async created() {
      var temp = await axios.get(axios.backend + '/dashes')

      this.dash = temp.data._embedded.dashes;

    },
    methods: {
    }
  }
</script>

