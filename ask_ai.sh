#!/bin/bash

# La URL del tuo backend Spring Boot
SERVER_URL="http://localhost:8080/api/ask"

# Avviso iniziale ⚠️
echo "🚀 Benvenuto nella shell AI con esecuzione comandi! 🤖💬"
echo "🔹 Usa '-exec' per eseguire automaticamente i comandi suggeriti dall'AI."
echo "⚠️ FUNZIONE SPERIMENTALE: Attenzione ai comandi eseguiti! ⚠️"
echo "🔹 Usa '-q' per uscire."

while true; do
    echo -n "Scrivi una domanda (-q per uscire): "
    read user_input

    # Se l'input è "-q", esci
    if [ "$user_input" == "-q" ]; then
        echo "Uscita... 🛑"
        break
    fi

    # Se la domanda contiene '-exec'
    if [[ "$user_input" == *"-exec"* ]]; then
        # Rimuovi -exec dalla domanda
        clean_question=$(echo "$user_input" | sed 's/-exec//g' | sed 's/^[[:space:]]*//g')

        # Richiesta a GPT-4
        response=$(curl -s -X POST "$SERVER_URL" \
            -H "Content-Type: application/json" \
            -d '{"question": "'"$clean_question"'"}')

        echo "Risposta da GPT-4: $response"

        # 🔹 FILTRIAMO SOLO COMANDI VALIDI 🔹
        # 1. Rimuoviamo apici, virgolette, e backtick
        clean_response=$(echo "$response" | sed 's/[`"'"'"']//g')

        # 2. Estraiamo il primo comando valido
        command_to_execute=""
        for word in $clean_response; do
            if command -v "$word" >/dev/null 2>&1; then
                command_to_execute="$word"
                break  # Prendiamo solo il primo comando valido
            fi
        done

        # Se abbiamo trovato un comando valido, eseguiamolo
        if [[ -n "$command_to_execute" ]]; then
            echo "⚠️ ATTENZIONE: verrà eseguito il comando 👉 $command_to_execute"
            output=$(eval "$command_to_execute" 2>&1)
            echo "Risultato del comando: $output"
        else
            echo "Errore: nessun comando valido trovato nella risposta. ❌"
        fi
    else
        # Richiesta normale senza -exec
        response=$(curl -s -X POST "$SERVER_URL" \
            -H "Content-Type: application/json" \
            -d '{"question": "'"$user_input"'"}')

        echo "Risposta da GPT-4: $response"
    fi
done
