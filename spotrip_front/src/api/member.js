import { localAxios } from "@/util/http-commons";

const local = localAxios();

const memberUrl = "/member";

function getMember(memberid, success, fail) {
  local.get(`${memberUrl}/${memberid}`).then(success).catch(fail);
}
function registMember(member, success, fail) {
    local.post(`${memberUrl}`, JSON.stringify(member)).then(success).catch(fail);
  }
function updateMember(member, success, fail) {
  local.put(`${memberUrl}`, JSON.stringify(member)).then(success).catch(fail);
}
function deleteMember(memberid, success, fail) {
  local.delete(`${memberUrl}/${memberid}`).then(success).catch(fail);
}
async function userConfirm(param, success, fail) {
  await local.post(`/member/login`, param).then(success).catch(fail);
}   

async function findById(memberid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/member/info/${memberid}`).then(success).catch(fail);
}

async function tokenRegeneration(member, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/member/refresh`, member).then(success).catch(fail);
}

async function logout(memberid, success, fail) {
  await local.get(`/member/logout/${memberid}`).then(success).catch(fail);
}

export {
    getMember,
    registMember,
    updateMember,
    deleteMember,
    userConfirm, 
    findById, 
    tokenRegeneration, 
    logout
};
