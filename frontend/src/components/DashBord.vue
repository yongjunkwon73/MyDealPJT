<template>

<v-data-table
    :headers="headers"
    :items="dashBord"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'DashBord',
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
            { text: "puStatus", value: "puStatus" },
            { text: "puDate", value: "puDate" },
            { text: "puCancelDate", value: "puCancelDate" },
            { text: "payId", value: "payId" },
            { text: "payStatus", value: "payStatus" },
            { text: "payDate", value: "payDate" },
            { text: "conId", value: "conId" },
            { text: "conStatus", value: "conStatus" },
            { text: "conDate", value: "conDate" },
            { text: "conCancelDate", value: "conCancelDate" },
            { text: "carId", value: "carId" },
            { text: "salePrice", value: "salePrice" },
        ],
        dashBord : [],
    }),
    async created() {
      var temp = await axios.get(axios.backend + '/dashbords')

      this.dashBord = temp.data._embedded.dashbords;

    },
    methods: {
    }
  }
</script>

