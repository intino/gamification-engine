
var newHealth = Math.max(dest.maxHealth() - src.maxHealth() / 2.0, 0.0)
dest.maxHealth(newHealth)

println(dest.id() + " health = " + dest.maxHealth())