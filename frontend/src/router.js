
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import PurchaseManager from "./components/PurchaseManager"

import BillingManager from "./components/BillingManager"

import ConsignManager from "./components/ConsignManager"

import StockManager from "./components/StockManager"


import DashBord from "./components/DashBord"
export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/purchases',
                name: 'PurchaseManager',
                component: PurchaseManager
            },

            {
                path: '/billings',
                name: 'BillingManager',
                component: BillingManager
            },

            {
                path: '/consigns',
                name: 'ConsignManager',
                component: ConsignManager
            },

            {
                path: '/stocks',
                name: 'StockManager',
                component: StockManager
            },


            {
                path: '/dashBords',
                name: 'DashBord',
                component: DashBord
            },


    ]
})
