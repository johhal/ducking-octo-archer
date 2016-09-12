var zz = argument0;

var waterLevel = 50;
var beachLevel = 55;
var grassLevel = 65;
var forrestLevel = 75;
var mountainLevel = 80;
var glacierLevel = 90;

if(zz < waterLevel){
    return instance_create(0, 0,obj_water);
}
if(zz < beachLevel){
    return instance_create(0, 0,obj_beach);
}if(zz < grassLevel){
    return instance_create(0, 0,obj_grassland);
}if(zz < forrestLevel){
    return instance_create(0, 0,obj_forrest);
}if(zz < mountainLevel){
    return instance_create(0, 0,obj_mountain);
}if(zz < glacierLevel){
    return instance_create(0, 0,obj_glacier);
}


