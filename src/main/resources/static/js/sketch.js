const form = document.querySelector('#params')
const currPal = document.querySelector('#currentPalette')
const formImg = document.querySelector('#image')
const parameters = document.querySelector('#parameters')
const par1 = document.querySelector('#showOutlines')
const par2 = document.querySelector('#outlineWidth')
const par3 = document.querySelector('#outlineColor')
const par4 = document.querySelector('#opacity')
const par5 = document.querySelector('#minStrokeWidth')
const par6 = document.querySelector('#maxStrokeWidth')
const par7 = document.querySelector('#strokeAngle')
const par8 = document.querySelector('#layers')
const par9 = document.querySelector('#colorFill')
const par10 = document.querySelector('#colorSelection')
const par11 = document.querySelector('#palette')
const formdiv = document.querySelector('#upload')
const title = document.querySelector('#title')
const message = document.querySelector('#message')
const preview = document.querySelector('#preview')
const input = document.querySelector('input[name="image"]');
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
  table = loadTable("../../palettes/colors.csv", "csv", "header");
}

function regen(e){
  e.preventDefault();
  message.classList.add("vanish")
  formdiv.classList.add("show");
  title.value = "";
  const formData = new FormData(e.target);
  const data = Object.fromEntries(new FormData(e.target).entries());
  let params = "";
  if(data.lines==="Yes"){
    lines = true;
    params+= "Show Outlines: Yes, ";
  } else {
    lines = false;
    params+= "Show Outlines: No, ";
  }
  sw = Number(data.linewidth);
  params+= `Outline Width: ${data.linewidth}px, `;
  if(data.linecolor==="On"){
    colorLines = true;
    params+= "Outline Color: On, ";
  } else {
    colorLines = false;
    params+= "Outline Color: Off, ";
  }
  alph = Number(data.opacity);
  params+= `Stroke Opacity: ${data.opacity}, `;
  minYchange = Number(data.minswidth);
  params+= `Min Stroke Width: ${data.minswidth}, `;
  maxYchange = Number(data.maxswidth);
  params+= `Max Stroke Width: ${data.maxswidth}, `;
  rotStripe = Number(data.rotation);
  params+= `Stroke Angle: ${data.rotation}, `;
  layers = Number(data.layers);
  params+= `Number Of Layers: ${data.layers}, `;
  if(data.strokefill==="On"){
    filling = true;
    params+= "Stroke Fill: On, ";
  } else {
    filling = false;
    params+= "Stroke Fill: Off, ";
  }
  if(data.random==="Totally Random"){
    colRand = true;
    params+= "Color Selection: Random, ";
  } else {
    colRand = false;
    params+= "Color Selection: Palette Based, ";
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
    params+= `Palette Number: N/A`
  } else if(Number(data.palnum) === 0){
    currPal.innerHTML=`Current Palette: ${palette}`;
    params+= `Palette Number: ${palette}`;
  } else {
    currPal.innerHTML=`Current Palette: ${palette}`;
    params+= `Palette Number: ${palette}`;
  }
  par1.value = data.lines;
  par2.value = data.linewidth;
  par3.value = data.linecolor;
  par4.value = data.opacity;
  par5.value = data.minswidth;
  par6.value = data.maxswidth;
  par7.value = data.rotation;
  par8.value = data.layers;
  par9.value = data.strokefill;
  par10.value = data.random;
  par11.value = palette;
  parameters.value = params;
  formImg.value = img;
  preview.src = img;
  console.log(params)
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