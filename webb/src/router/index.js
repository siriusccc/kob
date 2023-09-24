import { createRouter, createWebHistory } from 'vue-router'
import PkindexView from '../views/pk/PkindexView'
import RecordView from '../views/record/RecordView'
import RanklistView from '../views/rankinglist/RanklistView'
import BotView from '../views/user/bot/BotView'
import NotFound from '../views/error/NotFound'

const routes = [
  {
    path:"/",
    name:"home",
    redirect:"/pk/",
  },
  {
    path:"/pk/",
    name: "pk_index",
    component: PkindexView,
  },
  {
    path:"/ranklist/",
    name: "ranklist_index",
    component: RanklistView,
  },
  {
    path:"/record/",
    name: "record_index",
    component: RecordView,
  },
  {
    path:"/user/bot/",
    name: "user_bot_index",
    component: BotView,
  },
  {
    path:"/404/",
    name: "404",
    component: NotFound,
  },
  {
    path:"/:catchAll(.*)",
    redirect:"/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
 
export default router


