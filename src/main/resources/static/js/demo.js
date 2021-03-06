const form = document.querySelector('#params')
const currPal = document.querySelector('#currentPalette')
let minYchange = 0; //these two ranges determine line overlap and width
let maxYchange = 9;
let layers = 5;
let rotStripe = 0; //rotation of each stripe; try 10 or 90;
// try lines = true with high alph or lines = false with low alph (100)
let lines = false;
let alph = 0; //out of 255
let colRand = false; //true = random color; false = color from palette table
let filling = true;
let colorLines = false; //false for black lines
let sw = 3; //line width
let extraBlack = 0; //1 for some black line and white fills; 0 for neither; -2 for fewer colors;
let extraBlackAlph = 255; //out of 255 - used if extraBlack=1 & lines, filling, colorLines all true, low alph, high sw
let r, g, b;
let table;
let data;
let palette;
let palettenum = 0;
let currentcanvas;
function preload() {
  table = loadTable("../palettes/colors.csv", "csv", "header");
}

function regen(e){
  e.preventDefault();
  const formData = new FormData(e.target);
  const data = Object.fromEntries(new FormData(e.target).entries());
  if(data.lines==="Yes"){
    lines = true;
  } else {
    lines = false;
  }
  sw = Number(data.linewidth);
  params+= `Outline Width: ${data.linewidth}, `;
  if(data.linecolor==="On"){
    colorLines = true;
  } else {
    colorLines = false;
  }
  alph = Number(data.opacity);
  minYchange = Number(data.minswidth);
  maxYchange = Number(data.maxswidth);
  rotStripe = Number(data.rotation);
  layers = Number(data.layers);
  if(data.strokefill==="On"){
    filling = true;
  } else {
    filling = false;
  }
  if(data.random==="Totally Random"){
    colRand = true;
  } else {
    colRand = false;
  }
  if(Number(data.palnum) === 0){
    palettenum = floor(random(676));
  } else {
    palettenum = Number(data.palnum);
  }
  setup();
  convert();
  if(data.random === "Totally Random"){
    currPal.innerHTML=`Random Colors Were Selected`;
  } else if(Number(data.palnum) === 0){
    currPal.innerHTML=`Current Palette: ${palette}`;
  } else {
    currPal.innerHTML=`Current Palette: ${palette}`;
  }
}

function setup() {
  let canv = createCanvas(800, 800);
  canv.parent('myCanvas');
  if (lines == true) {
    stroke(0, 0, 0, extraBlackAlph);
    strokeWeight(sw);
  } else {
    noStroke();
  }
  angleMode(DEGREES);
  let end = height / 2 + 500; //where lines stop
  palette = palettenum;
  for (let i = 0; i < layers; i++) {
    let y1;
    if (i == 0) {
      y1 = -height / 2 - 300;
    } else {
      y1 = -height / 2 + (height / layers) * i;
    }
    //starting height for each layer
    let y2 = y1,
      y3 = y1,
      y4 = y1,
      y5 = y1,
      y6 = y1;
    let rotLayer = random(359.99); //layer rotation
    let rotThisStripe = 0;
    //keep going until all the lines are at the bottom
    while (
      (y1 < end) &
      (y2 < end) &
      (y3 < end) &
      (y4 < end) &
      (y5 < end) &
      (y6 < end) &
      (-maxYchange < minYchange)
    ) {
      y1 += random(minYchange, maxYchange);
      y2 += random(minYchange, maxYchange);
      y3 += random(minYchange, maxYchange);
      y4 += random(minYchange, maxYchange);
      y5 += random(minYchange, maxYchange);
      y6 += random(minYchange, maxYchange);
      if (colRand == true) {
        r = random(256);
        g = random(256);
        b = random(256);
      } else {
        let col = floor(random(5 + extraBlack));
        r = table.get(palette, col * 3);
        g = table.get(palette, col * 3 + 1);
        b = table.get(palette, col * 3 + 2);
      }
      if (filling == true) {
        fill(r, g, b, alph);
      } else {
        noFill();
      }
      if (colorLines == true) {
        stroke(r, g, b, alph);
      }
      push();
      translate(width / 2, height / 2);
      rotThisStripe += rotStripe; //rotating after each stripe
      rotate(rotThisStripe + rotLayer);
      let xStart = -width / 2;
      beginShape();
      curveVertex(xStart - 300, height / 2 + 500);
      curveVertex(xStart - 300, y1);
      curveVertex(xStart + (width / 5) * 1, y2);
      curveVertex(xStart + (width / 5) * 2, y3);
      curveVertex(xStart + (width / 5) * 3, y4);
      curveVertex(xStart + (width / 5) * 4, y5);
      curveVertex(width / 2 + 300, y6);
      curveVertex(width / 2 + 300, height / 2 + 500);
      endShape(CLOSE);
      pop();
    }
  }
}

function convert() {
  currentcanvas = document.querySelector("canvas");
  img = currentcanvas.toDataURL('image/png');
}

form.onsubmit= regen;
//Shouts Out to Steve @ https://www.youtube.com/c/StevesMakerspace for providing a guide to creating the Abstract art creator. Definitely check his channel out for more p5 goodies!