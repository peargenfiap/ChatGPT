package br.com.fiap.chatap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.chatap.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupButtons()
        binding.textInputEditTextMessage.setText(
            // pega o valor que está vindo da action de origem (outro aplicativo)
            intent.extras?.getString(Intent.EXTRA_TEXT) /* ?: "Olá" (se vier null, setar como padrão */
        )
    }

    private fun setupButtons() {
        binding.buttonSendMessage.setOnClickListener() {
            binding.messageValue.text = binding.textInputEditTextMessage.text
            // limpa o valor do input
            clearText()
        }

        binding.buttonShareMessage.setOnClickListener() {
            /** quando o usuáro clicar em compartilhar ele vai criar um objeto
             * intent  com as informações necessárias para ser compartilhada
             * com outra activity de outro aplicativo. */
            val sendIntent: Intent = Intent().apply {
                // action de envio par aoutro app
                action = Intent.ACTION_SEND
                // tipo de envio (no caso desse app uma string)
                type = "text/plain"
                // aqui vem o valor à ser enviado
                putExtra(
                    // variável que será usada pelos outros apps para identificar nossa mensagem
                    Intent.EXTRA_TEXT,
                    // valor da mensagem que o usuário digitou no input
                    binding.messageValue.text.toString()
                )
            }
            // cria um objeto para ser enviado para a outra activity
            val shareIntent = Intent.createChooser(
                sendIntent,
                getString(R.string.chooser_title)
            )
            startActivity(shareIntent)
        }
    }

    private fun clearText() {
        binding.textInputEditTextMessage.text?.clear();
    }
}