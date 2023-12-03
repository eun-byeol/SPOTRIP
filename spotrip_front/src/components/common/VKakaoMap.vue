<script setup>
import { ref, watch, onMounted, defineProps } from "vue";

var map;
const positions = ref([]);
const markers = ref([]);
const marker = ref(null);
const props = defineProps({ attractions: Array, selectAttraction: Object, type: String });

// 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
var linePath = [];
var polyline;

watch(
  () => props.selectAttraction.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(props.selectAttraction.latitude, props.selectAttraction.longitude);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY}&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

watch(
  () => props.attractions.value,
  () => {
    const isEmpty = addPosition();
    if (isEmpty) {
      currentLocation();
    }
    else { // 빈 배열이 아닐 때만 마커 로드
      loadMarkers();
    }
  },
  { deep: true }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);


  const isEmpty = addPosition();
  if (isEmpty) {
    currentLocation();
  }
  else {
    loadMarkers();
  }
};

function addPosition() {
  positions.value = [];
  linePath = [];
  let isEmpty = true; // positions 배열이 빈 값인지 체크하는 flag
  props.attractions.forEach((attraction) => {
    // console.log(attraction.latitude, attraction.longitude);
    isEmpty = false;
    let obj = {};
    obj.id = attraction.id;
    obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
    obj.title = attraction.title;
    obj.addr1 = attraction.addr1;
    obj.tel = attraction.tel;

    linePath.push(new kakao.maps.LatLng(attraction.latitude, attraction.longitude)); // 상세보기 조회시 마커들을 연결한 선

    positions.value.push(obj);
  })

  // 지도에 표시할 선을 생성합니다
  polyline = new kakao.maps.Polyline({
    path: linePath, // 선을 구성하는 좌표배열 입니다
    strokeWeight: 5, // 선의 두께 입니다
    strokeColor: '#008000', // 선의 색깔입니다
    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: 'solid' // 선의 스타일입니다
  });

  return isEmpty;
};

function currentLocation() {
  // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
  if (navigator.geolocation) {

    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {

      var lat = position.coords.latitude, // 위도
        lon = position.coords.longitude; // 경도

      var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
      var message = '<div style="padding:5px; color:black;">현위치</div>'; // 인포윈도우에 표시될 내용입니다

      // 마커와 인포윈도우를 표시합니다
      displayMarker(locPosition, message);
    });
  } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

    var locPosition = new kakao.maps.LatLng(37.4812845080678, 126.952713197762),
      message = '현재 위치를 알 수 없어 기본 위치로 이동합니다.'

    displayMarker(locPosition, message);
  }
  return true
}

function displayMarker(locPosition, message) {
  // If there's no marker, create one
  if (!marker.value) {
    var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    var imageSize = new kakao.maps.Size(24, 35);
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    marker.value = new kakao.maps.Marker({
      map: map,
      position: locPosition,
      image: markerImage,
    });
  } else {
    // Update the existing marker's position
    marker.value.setPosition(locPosition);
  }

  var iwContent = message,
    iwRemoveable = true;

  // If there's no infowindow, create one
  if (!marker.infowindow) {
    marker.infowindow = new kakao.maps.InfoWindow({
      content: iwContent,
      removable: iwRemoveable,
    });
  } else {
    // Update the existing infowindow's content
    marker.infowindow.setContent(iwContent);
  }

  // Open the infowindow
  marker.infowindow.open(map, marker.value);

  // Set the map center to the new position
  map.setCenter(locPosition);
}

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);

  // 상세보기일때, 마커들을 연결한 경로 보여주기
  if (props.type === 'detail') {
    // console.log("마커 선 연결!!!!", linePath);
    polyline.setMap(map);
  }
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 700px;
}
</style>
