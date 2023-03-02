# Setup

Для подключения к брокеру вам нужно устоновить python-qpid-proton.
Текущее решение успешно проверено на версии 0.38.0.

pip install python-qpid-proton

# Debug
Для включения логов logging.basicConfig(level=logging.DEBUG)

Для включения расширенного логирования можно выставить переменные окружения:
* PN_TRACE_FRM=1
* PN_LOG=trace+

# Полезные ссылки
* [Оффициальный сайт Qpid Proton](https://qpid.apache.org/proton/index.html)
* [Настройка логирования](https://github.com/apache/qpid-proton/blob/main/c/docs/logging.md)
* [Gitlab](https://github.com/apimeister/qpid-proton-sys-rs/tree/main/lib/qpid-proton-0.34.0/python)

