const skills = {
  "Java" : 1,
  "Python" : 2,
  "Javascript" : 3,
  "Android" : 4,
  "Data Science" : 5,
  "Big Data" : 6,
  "Spring" : 7
};


const locations = {
  Mumbai :{
    latitude : 19.076,
    longitude : 72.877
  },
  Delhi : {
    latitude : 28.704,
    longitude : 88.363
  },
  Chennai : {
    latitude : 13.0827,
    longitude : 80.2707
  },
  Pune : {
    latitude : 18.5204,
    longitude : 73.8576
  },
  Bangaluru : {
    latitude : 12.9716,
    longitude : 77.5946
  }
}

var selectedKeywords = new Set();
var selectedLocation = null;
var jobSearchResults = null;

function delay(millisecs) {
  var stopTime = Date.now() + millisecs;

  while (Date.now() < stopTime);
  console.log("Exiting");
}