///scpt_update_grid_pos(grid_pos_x, grid_pos_y);

var xpos = argument[0];
var ypos = argument[1];

if(scpt_inside_world(xpos, ypos))
{
    if(walk_permission == global.world_map[#xpos, ypos].my_land_type)
    {
        xpos_grid = xpos;
        ypos_grid = ypos;

        x = xpos_grid * obj_grassland.sprite_width;
        y = ypos_grid * obj_grassland.sprite_height;
    }
}
