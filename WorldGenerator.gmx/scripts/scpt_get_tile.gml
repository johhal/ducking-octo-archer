var zz = argument0;

var waterLevel = 50;
var mountainLevel = 70;
if(zz < waterLevel){
    return 1;
}else if(zz > mountainLevel){
    return 2;
} else{
    return 0;
}

