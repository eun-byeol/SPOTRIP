<script setup>
defineProps({ article: Object, index: Number });
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ index + 1 }}</th>
    <td class="text-start">
      <router-link :to="{ name: 'article-view', params: { articleid: article.id } }" class="article-title link-dark">
        {{ article.title }}
      </router-link>
    </td>
    <td>{{ article.category }}</td>
    <td>{{ article.arrivalDate }}</td>
    <td>{{ article.departureDate }}</td>
    <td>{{ article.memberId }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
