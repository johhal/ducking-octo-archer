var dir = argument[0];
var vision = argument[1];

scpt_update_vision(vision, x, y, -1);
switch(dir){
    case directions.right:
        x += obj_grassland.sprite_width;
        break;
    case directions.left:
        x -= obj_grassland.sprite_width;
        break;
    case directions.up:
        y -= obj_grassland.sprite_height;
        break;
    case directions.down:
        y += obj_grassland.sprite_height;
        break;
    default:
        // Du är inte snäll om du skickar in något annat
        break;
}
scpt_update_vision(vision, x, y, 1);
