var dir = argument[0];
var distance = argument[1];
var vision = argument[2];

scpt_update_vision(vision, x, y, -1);
switch(dir){
    case directions.right:
        x += distance * obj_grassland.sprite_width;
        break;
    case directions.left:
        x -= distance * obj_grassland.sprite_width;
        break;
    case directions.up:
        y -= distance * obj_grassland.sprite_height;
        break;
    case directions.down:
        y += distance * obj_grassland.sprite_height;
        break;
}
scpt_update_vision(vision, x, y, 1);
