///scpt_inside_world(grid_pos_x, grid_pos_y);

var x_pos = argument[0];
var y_pos = argument[1];

if(x_pos < 0 || y_pos < 0 || x_pos >= global.world_width || y_pos >= global.world_height)
    return false;
return true;
