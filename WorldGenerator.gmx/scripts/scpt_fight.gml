/// scpt_fight(combatant1, combatant2);
var combatant1 = argument[0];
var combatant2 = argument[1];

var dmg1 = combatant1.strength - combatant2.armor;
var dmg2 = combatant2.strength - combatant1.armor;

if(dmg1 < 0) dmg1 = 0;
if(dmg2 < 0) dmg2 = 0;

combatant2.life -= dmg1;
combatant1.life -= dmg2;

show_debug_message("C1: " + string(combatant1.life) + "#C2: " + string(combatant2.life));

if(combatant1.life <= 0)
    combatant1.alarm[1] = 1;
if(combatant2.life <= 0) 
    combatant2.alarm[1] = 1;
//show_debug_message("C1: " + string(combatant1.life) + "#C2: " + string(combatant2.life));
