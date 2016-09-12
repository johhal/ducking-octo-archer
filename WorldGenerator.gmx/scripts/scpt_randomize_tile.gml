var weight_grassland=200;
var weight_water=50;
var weight_mountain=10;

var proximity_scale_grassland = 0.4;
var proximity_scale_water = 0.9;
var proximity_scale_mountain = 0.9;

//to be calculated
var grassland_limit=33;
var water_limit = 33;
var mountain_limit=33;


var effective_weight_grassland = weight_grassland;
var effective_weight_water = weight_water;
var effective_weight_mountain = weight_mountain;
//argument0 is north tile type
//argument1 is west tile type


if !is_real(argument0){
    return -1;
}
if !is_real(argument1){
    return -1;
}

//Adjust effective weight values depending on the current map.
//north is grass
if (argument0 = 0)
{
    effective_weight_grassland+=weight_grassland*proximity_scale_grassland;
}
//north is water
if (argument0 = 1)
{
    effective_weight_water+=weight_water*proximity_scale_water
}
//north is mountain
if (argument0 = 2)
{
    effective_weight_mountain+=weight_mountain*proximity_scale_mountain
}

//west is grass
if (argument1 = 0)
{
    effective_weight_grassland+=weight_grassland*proximity_scale_grassland;
}
//west is water
if (argument1 = 1)
{
    effective_weight_water+=weight_water*proximity_scale_water
}
//west is mountain
if (argument1 = 2)
{
    effective_weight_mountain+=weight_mountain*proximity_scale_mountain
}

//Calculate the real limits
grassland_limit= (effective_weight_grassland / (effective_weight_grassland+effective_weight_water+effective_weight_mountain))*100
water_limit= (effective_weight_water / (effective_weight_grassland+effective_weight_water+effective_weight_mountain))*100
mountain_limit= (effective_weight_mountain / (effective_weight_grassland+effective_weight_water+effective_weight_mountain))*100

var rand = irandom(100);
show_debug_message(grassland_limit);
if (rand <=grassland_limit)
{
return 0
}
if (rand <=(water_limit + grassland_limit))
{
return 1
}
if (rand <=(mountain_limit + grassland_limit + water_limit))
{
return 2
}
