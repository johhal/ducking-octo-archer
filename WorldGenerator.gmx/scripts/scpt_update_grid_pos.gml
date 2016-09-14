var xpos = argument[0];
var ypos = argument[1];

if(scpt_inside_world(xpos, ypos))
{
    xpos_grid = xpos;
    ypos_grid = ypos;

    x = xpos_grid * obj_grassland.sprite_width;
    y = ypos_grid * obj_grassland.sprite_height;
}
