import { localAxios } from "@/util/http-commons";

const local = localAxios();

const boardUrl = "/board";
const commentUrl = "/comment"; // 미구현

function listArticle(param, success, fail) {
  local.get(`${boardUrl}`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`${boardUrl}/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  // console.log("board article", article);
  local.post(`${boardUrl}`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.get(`${boardUrl}/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`${boardUrl}`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`${boardUrl}/${articleno}`).then(success).catch(fail);
}

function registComment(comment, success, fail) {
  console.log("boardjs comment", comment);
  local.post(`${commentUrl}`, JSON.stringify(comment)).then(success).catch(fail);
}

function listComment(articleno, success, fail) {
  local.get(`${commentUrl}/${articleno}`).then(success).catch(fail);
}

function deleteComment(commentno, success, fail) {
  local.delete(`${commentUrl}/${commentno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  registComment,
  listComment,
  deleteComment
};
