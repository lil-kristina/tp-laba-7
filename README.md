# tp-laba-7 Payment System - Clean Architecture

## Структура:
- **domain/** - бизнес-логика (Order, Money, OrderStatus)
- **application/** - use cases (PayOrderUseCase)
- **infrastructure/** - реализации (InMemoryOrderRepository, FakePaymentGateway)

## Принципы:
1. Domain не зависит от внешнего мира
2. Application координирует работу
3. Infrastructure реализует интерфейсы

## Тесты проверяют:
✅ Успешную оплату  
❌ Пустой заказ  
❌ Повторную оплату  
❌ Отказ платежного шлюза
