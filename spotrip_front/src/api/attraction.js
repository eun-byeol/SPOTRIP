import { localAxios } from "@/util/http-commons";

const local = localAxios();

const attractionUrl = "/attraction";

function listAttractions(param, success, fail) {
  console.log("listAttractions param 호출", param);
  local.get(`${attractionUrl}`, { params: param }).then(success).catch(fail);
}

export {
  listAttractions
};