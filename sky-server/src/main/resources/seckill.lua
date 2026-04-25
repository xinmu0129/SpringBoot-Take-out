-- KEYS[1]: 库存 Key (例如 seckill:stock:1)
-- KEYS[2]: 已买用户集合 Key (例如 seckill:user:1)
-- ARGV[1]: 用户 ID
-- ARGV[2]: 购买数量 (通常为 1)

-- 1. 判断用户是否已经参与过秒杀
if redis.call('sismember', KEYS[2], ARGV[1]) == 1 then
    return -1 -- 返回 -1 代表重复下单
end

-- 2. 判断库存是否足够
local stock = tonumber(redis.call('get', KEYS[1]))
if stock <= 0 or stock < tonumber(ARGV[2]) then
    return 0 -- 返回 0 代表库存不足
end

-- 3. 扣减库存并记录用户ID到Set集合中
redis.call('decrby', KEYS[1], ARGV[2])
redis.call('sadd', KEYS[2], ARGV[1])

return 1 -- 返回 1 代表秒杀成功，拿到了购买资格